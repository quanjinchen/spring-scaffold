package dn.spring.scaffold.console.pojo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "首页统计汇总")
public class DashboardSummaryDTO {

    private List<DashboardCardDTO> cards = new ArrayList<DashboardCardDTO>();

    private DashboardUserSeriesDTO userSeries = new DashboardUserSeriesDTO();

    private DashboardActiveSeriesDTO activeSeries = new DashboardActiveSeriesDTO();

    private DashboardRankSeriesDTO rankList = new DashboardRankSeriesDTO();

    private DashboardPieSeriesDTO pieSummary = new DashboardPieSeriesDTO();

    @Data
    public static class DashboardCardDTO {

        private Integer id;

        private String name;

        private Integer num;

        private String subLabel;

        private Integer subValue;

        private String icon;
    }

    @Data
    public static class DashboardStatsPointDTO {

        private String date;

        private Integer num;
    }

    @Data
    public static class DashboardRankItemDTO {

        private String name;

        private Integer num;
    }

    @Data
    public static class DashboardPieItemDTO {

        private String name;

        private Integer num;
    }

    @Data
    public static class DashboardUserSeriesDTO {

        private List<DashboardStatsPointDTO> WEEK = new ArrayList<DashboardStatsPointDTO>();

        private List<DashboardStatsPointDTO> MONTH = new ArrayList<DashboardStatsPointDTO>();

        private List<DashboardStatsPointDTO> YEAR = new ArrayList<DashboardStatsPointDTO>();
    }

    @Data
    public static class DashboardActiveBucketDTO {

        private List<DashboardStatsPointDTO> peopleList = new ArrayList<DashboardStatsPointDTO>();

        private List<DashboardStatsPointDTO> timesList = new ArrayList<DashboardStatsPointDTO>();
    }

    @Data
    public static class DashboardActiveSeriesDTO {

        private DashboardActiveBucketDTO WEEK = new DashboardActiveBucketDTO();

        private DashboardActiveBucketDTO MONTH = new DashboardActiveBucketDTO();

        private DashboardActiveBucketDTO YEAR = new DashboardActiveBucketDTO();
    }

    @Data
    public static class DashboardRankSeriesDTO {

        private List<DashboardRankItemDTO> TOTAL = new ArrayList<DashboardRankItemDTO>();

        private List<DashboardRankItemDTO> SUCCESS = new ArrayList<DashboardRankItemDTO>();
    }

    @Data
    public static class DashboardPieSeriesDTO {

        private List<DashboardPieItemDTO> STATUS = new ArrayList<DashboardPieItemDTO>();

        private List<DashboardPieItemDTO> MODULE = new ArrayList<DashboardPieItemDTO>();
    }
}

