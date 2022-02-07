<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>财经趋势</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="finance" style="height: 420px" />
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>健康趋势</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <div ref="health" style="height: 420px" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { getNewsTotalInfo } from "@/api/news/analysis";
  import echarts from "echarts";

  export default {
    name: "analysis",
    data() {
      return {
        // 财经趋势信息
        finance: null,
        // 健康趋势信息
        health: null
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询趋势信息 */
      getList() {
        this.finance = echarts.init(this.$refs.finance, "finance");
        this.finance.setOption({
          //x轴
          xAxis:{
            data:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
          },
          //y轴没有显式设置，根据值自动生成y轴
          yAxis:{},
          //数据-data是最终要显示的数据
          series:[{
            name:'虚假百分比',
            type:'line',
            data:[61, 63.5, 65, 64.2, 62.5, 66.3, 64.8, 68.5, 67.3, 64.5, 65, 66.1]
          }]
        });

        this.health = echarts.init(this.$refs.health, "health");
        this.health.setOption({
          //x轴
          xAxis:{
            data:["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"]
          },
          //y轴没有显式设置，根据值自动生成y轴
          yAxis:{},
          //数据-data是最终要显示的数据
          series:[{
            name:'虚假百分比',
            type:'line',
            data:[75, 76.4, 80, 81.6, 79.4, 82, 80.1, 78.4, 79.24, 74, 72.1, 73]
          }]
        });
      }
    },
  };
</script>
