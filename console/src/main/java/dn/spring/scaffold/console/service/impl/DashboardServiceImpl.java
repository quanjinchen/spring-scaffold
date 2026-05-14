package dn.spring.scaffold.console.service.impl;

import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.console.pojo.resp.DashboardSummaryDTO;
import dn.spring.scaffold.console.service.DashboardService;
import dn.spring.scaffold.system.entity.FaceAuthLog;
import dn.spring.scaffold.system.entity.OperationLog;
import dn.spring.scaffold.system.entity.User;
import dn.spring.scaffold.system.manager.FaceAuthLogManager;
import dn.spring.scaffold.system.manager.OperationLogManager;
import dn.spring.scaffold.system.manager.UserManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("MM-dd");

    private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    @Resource
    private UserManager userManager;
    @Resource
    private OperationLogManager operationLogManager;
    @Resource
    private FaceAuthLogManager faceAuthLogManager;

    @Override
    public RespInfo<DashboardSummaryDTO> summary() {
        List<User> users = userManager.listUsers(null);
        List<OperationLog> operationLogs = operationLogManager.listOperationLogs();
        List<FaceAuthLog> faceAuthLogs = faceAuthLogManager.listFaceAuthLogs(null);

        DashboardSummaryDTO summaryDTO = new DashboardSummaryDTO();
        summaryDTO.setCards(buildCards(users, faceAuthLogs));

        DashboardSummaryDTO.DashboardUserSeriesDTO userSeries = new DashboardSummaryDTO.DashboardUserSeriesDTO();
        userSeries.setWEEK(buildOperatorSeries(operationLogs, RangeType.WEEK));
        userSeries.setMONTH(buildOperatorSeries(operationLogs, RangeType.MONTH));
        userSeries.setYEAR(buildOperatorSeries(operationLogs, RangeType.YEAR));
        summaryDTO.setUserSeries(userSeries);

        DashboardSummaryDTO.DashboardActiveSeriesDTO activeSeries = new DashboardSummaryDTO.DashboardActiveSeriesDTO();
        activeSeries.setWEEK(buildActiveSeries(operationLogs, RangeType.WEEK));
        activeSeries.setMONTH(buildActiveSeries(operationLogs, RangeType.MONTH));
        activeSeries.setYEAR(buildActiveSeries(operationLogs, RangeType.YEAR));
        summaryDTO.setActiveSeries(activeSeries);

        DashboardSummaryDTO.DashboardRankSeriesDTO rankSeries = new DashboardSummaryDTO.DashboardRankSeriesDTO();
        rankSeries.setTOTAL(buildRankList(operationLogs, false));
        rankSeries.setSUCCESS(buildRankList(operationLogs, true));
        summaryDTO.setRankList(rankSeries);

        DashboardSummaryDTO.DashboardPieSeriesDTO pieSeries = new DashboardSummaryDTO.DashboardPieSeriesDTO();
        pieSeries.setSTATUS(buildStatusPieSummary(operationLogs, faceAuthLogs));
        pieSeries.setMODULE(buildModulePieSummary(operationLogs));
        summaryDTO.setPieSummary(pieSeries);
        return RespInfo.success(summaryDTO);
    }

    private List<DashboardSummaryDTO.DashboardCardDTO> buildCards(
            List<User> users,
            List<FaceAuthLog> faceAuthLogs
    ) {
        int enabledUserCount = 0;
        for (User user : users) {
            if (user.getStatus() != null && user.getStatus().intValue() == 1) {
                enabledUserCount++;
            }
        }

        LocalDateTime lastWeekStart = LocalDate.now().minusDays(6).atStartOfDay();
        int recentFaceAuthCount = 0;
        for (FaceAuthLog faceAuthLog : faceAuthLogs) {
            if (faceAuthLog.getCreateTime() != null && !faceAuthLog.getCreateTime().isBefore(lastWeekStart)) {
                recentFaceAuthCount++;
            }
        }

        List<DashboardSummaryDTO.DashboardCardDTO> cards = new ArrayList<DashboardSummaryDTO.DashboardCardDTO>();
        cards.add(buildCard(1, "用户总数", users.size(), "启用用户", enabledUserCount, "User"));
        cards.add(buildCard(2, "认证日志数", faceAuthLogs.size(), "近7天认证", recentFaceAuthCount, "Histogram"));
        return cards;
    }

    private DashboardSummaryDTO.DashboardCardDTO buildCard(
            int id,
            String name,
            int num,
            String subLabel,
            int subValue,
            String icon
    ) {
        DashboardSummaryDTO.DashboardCardDTO card = new DashboardSummaryDTO.DashboardCardDTO();
        card.setId(id);
        card.setName(name);
        card.setNum(num);
        card.setSubLabel(subLabel);
        card.setSubValue(subValue);
        card.setIcon(icon);
        return card;
    }

    private List<DashboardSummaryDTO.DashboardStatsPointDTO> buildOperatorSeries(List<OperationLog> logs, RangeType rangeType) {
        List<TimeBucket> buckets = buildBuckets(rangeType);
        List<DashboardSummaryDTO.DashboardStatsPointDTO> result = new ArrayList<DashboardSummaryDTO.DashboardStatsPointDTO>();
        for (TimeBucket bucket : buckets) {
            Set<String> operators = new LinkedHashSet<String>();
            for (OperationLog log : logs) {
                if (!inBucket(log.getRequestTime(), bucket)) {
                    continue;
                }
                operators.add(normalizeOperatorName(log.getOperatorName()));
            }
            DashboardSummaryDTO.DashboardStatsPointDTO point = new DashboardSummaryDTO.DashboardStatsPointDTO();
            point.setDate(bucket.getLabel());
            point.setNum(operators.size());
            result.add(point);
        }
        return result;
    }

    private DashboardSummaryDTO.DashboardActiveBucketDTO buildActiveSeries(List<OperationLog> logs, RangeType rangeType) {
        List<TimeBucket> buckets = buildBuckets(rangeType);
        DashboardSummaryDTO.DashboardActiveBucketDTO bucketDTO = new DashboardSummaryDTO.DashboardActiveBucketDTO();
        for (TimeBucket bucket : buckets) {
            Set<String> operators = new LinkedHashSet<String>();
            int times = 0;
            for (OperationLog log : logs) {
                if (!inBucket(log.getRequestTime(), bucket)) {
                    continue;
                }
                operators.add(normalizeOperatorName(log.getOperatorName()));
                times++;
            }

            DashboardSummaryDTO.DashboardStatsPointDTO peoplePoint = new DashboardSummaryDTO.DashboardStatsPointDTO();
            peoplePoint.setDate(bucket.getLabel());
            peoplePoint.setNum(operators.size());
            bucketDTO.getPeopleList().add(peoplePoint);

            DashboardSummaryDTO.DashboardStatsPointDTO timesPoint = new DashboardSummaryDTO.DashboardStatsPointDTO();
            timesPoint.setDate(bucket.getLabel());
            timesPoint.setNum(times);
            bucketDTO.getTimesList().add(timesPoint);
        }
        return bucketDTO;
    }

    private List<DashboardSummaryDTO.DashboardRankItemDTO> buildRankList(List<OperationLog> logs, boolean successOnly) {
        Map<String, Integer> countMap = new LinkedHashMap<String, Integer>();
        for (OperationLog log : logs) {
            if (successOnly && !Boolean.TRUE.equals(log.getSuccessFlag())) {
                continue;
            }
            String moduleName = normalizeModuleName(log.getModuleName());
            countMap.put(moduleName, countMap.getOrDefault(moduleName, 0) + 1);
        }
        return countMap.entrySet().stream()
                .sorted((left, right) -> Integer.compare(right.getValue(), left.getValue()))
                .limit(6)
                .collect(ArrayList::new, (list, entry) -> {
                    DashboardSummaryDTO.DashboardRankItemDTO item = new DashboardSummaryDTO.DashboardRankItemDTO();
                    item.setName(entry.getKey());
                    item.setNum(entry.getValue());
                    list.add(item);
                }, ArrayList::addAll);
    }

    private List<DashboardSummaryDTO.DashboardPieItemDTO> buildStatusPieSummary(
            List<OperationLog> operationLogs,
            List<FaceAuthLog> faceAuthLogs
    ) {
        int successCount = 0;
        int failedCount = 0;

        for (OperationLog log : operationLogs) {
            if (Boolean.TRUE.equals(log.getSuccessFlag())) {
                successCount++;
            } else {
                failedCount++;
            }
        }
        for (FaceAuthLog log : faceAuthLogs) {
            if (log.getStatus() != null && log.getStatus().intValue() == 1) {
                successCount++;
            } else {
                failedCount++;
            }
        }

        List<DashboardSummaryDTO.DashboardPieItemDTO> result = new ArrayList<DashboardSummaryDTO.DashboardPieItemDTO>();
        if (successCount > 0) {
            result.add(buildPieItem("成功", successCount));
        }
        if (failedCount > 0) {
            result.add(buildPieItem("失败", failedCount));
        }
        return result;
    }

    private List<DashboardSummaryDTO.DashboardPieItemDTO> buildModulePieSummary(List<OperationLog> operationLogs) {
        List<DashboardSummaryDTO.DashboardRankItemDTO> rankItems = buildRankList(operationLogs, false);
        List<DashboardSummaryDTO.DashboardPieItemDTO> result = new ArrayList<DashboardSummaryDTO.DashboardPieItemDTO>();
        for (int index = 0; index < rankItems.size() && index < 5; index++) {
            DashboardSummaryDTO.DashboardRankItemDTO rankItem = rankItems.get(index);
            result.add(buildPieItem(rankItem.getName(), rankItem.getNum()));
        }
        return result;
    }

    private DashboardSummaryDTO.DashboardPieItemDTO buildPieItem(String name, int num) {
        DashboardSummaryDTO.DashboardPieItemDTO item = new DashboardSummaryDTO.DashboardPieItemDTO();
        item.setName(name);
        item.setNum(num);
        return item;
    }

    private String normalizeModuleName(String moduleName) {
        return moduleName == null || "".equals(moduleName.trim()) ? "未归类模块" : moduleName.trim();
    }

    private String normalizeOperatorName(String operatorName) {
        return operatorName == null || "".equals(operatorName.trim()) ? "未知操作人" : operatorName.trim();
    }

    private boolean inBucket(LocalDateTime requestTime, TimeBucket bucket) {
        if (requestTime == null) {
            return false;
        }
        return !requestTime.isBefore(bucket.getStart()) && !requestTime.isAfter(bucket.getEnd());
    }

    private List<TimeBucket> buildBuckets(RangeType rangeType) {
        List<TimeBucket> buckets = new ArrayList<TimeBucket>();
        LocalDate today = LocalDate.now();
        if (rangeType == RangeType.WEEK) {
            for (int index = 6; index >= 0; index--) {
                LocalDate date = today.minusDays(index);
                buckets.add(new TimeBucket(date.format(DAY_FORMATTER), date.atStartOfDay(), date.atTime(23, 59, 59)));
            }
            return buckets;
        }

        if (rangeType == RangeType.MONTH) {
            for (int index = 3; index >= 0; index--) {
                LocalDate endDate = today.minusDays(index * 7L);
                LocalDate startDate = endDate.minusDays(6);
                buckets.add(new TimeBucket(startDate.format(DAY_FORMATTER), startDate.atStartOfDay(), endDate.atTime(23, 59, 59)));
            }
            return buckets;
        }

        for (int index = 11; index >= 0; index--) {
            YearMonth yearMonth = YearMonth.from(today.minusMonths(index));
            buckets.add(new TimeBucket(
                    yearMonth.format(MONTH_FORMATTER),
                    yearMonth.atDay(1).atStartOfDay(),
                    yearMonth.atEndOfMonth().atTime(23, 59, 59)
            ));
        }
        return buckets;
    }

    private enum RangeType {
        WEEK,
        MONTH,
        YEAR
    }

    private static class TimeBucket {

        private final String label;

        private final LocalDateTime start;

        private final LocalDateTime end;

        private TimeBucket(String label, LocalDateTime start, LocalDateTime end) {
            this.label = label;
            this.start = start;
            this.end = end;
        }

        public String getLabel() {
            return label;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public LocalDateTime getEnd() {
            return end;
        }
    }
}
