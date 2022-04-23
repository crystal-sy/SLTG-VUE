<template>
  <div class="search-page home">
    <div class="navbar">
      <div class="home-header-bar">
        <span class="homeTitle">消息通知</span>
      </div>
    </div>
    <div v-loading="loading" class="contentNav">
      <div v-for="(item, index) in noticeList" class="noticeContent">
        <p style="font-size: 16px; font-weight: bold; color: #000; margin-right: 3.4rem;">{{item.noticeTitle}}</p>
        <div>
          <div style=" font-size: 14px; margin-top: 0.6rem; color: #000;">
            <span style="margin-right: 0.2rem;" v-if="item.noticeType === '1'">通知</span>
            <span style="margin-right: 0.2rem;" v-if="item.noticeType === '2'">公告</span>
            <span style="float: right; font-size: 12px;">{{item.createTime}}</span>
          </div>
        </div>
        <div class="bottom_line"/>
      </div>
    </div>
    <div class="pulldownload" v-show="downLoadMore" @click="pulldownloadmore()">
      点击加载更多
    </div>
    <div class="nav">
      <div class="navItems" @click="go_home()">
        <div class="bottom_nav_icon icon_1"></div>
        <div class="bottom_nav_text">首页</div>
      </div>
      <div class="navItems">
        <div class="bottom_nav_icon icon_2" @click="go_collect()"></div>
        <div class="bottom_nav_text">收藏</div>
      </div>
      <div class="navItems" @click="go_my()">
        <div class="bottom_nav_icon icon_3"></div>
        <div class="bottom_nav_text_on">我的</div>
      </div>
    </div>
  </div>
</template>

<script>
  import { listNotice } from "@/api/system/notice";

  export default {
    name: 'Notice',
    data() {
      return {
        // 遮罩层
        loading: true,
        // 加载
        downLoadMore: false,
        // 通知消息表格数据
        noticeList: null,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          noticeTitle: undefined,
          status: undefined
        }
      }
    },
    created() {
      this.getList();
    },
    methods: {
      go_home() {
        this.$router.push("/home");
      },
      go_collect() {
        this.$router.push("/news/collect");
      },
      go_my() {
        this.$router.push("/system/user");
      },
      /** 查询通知消息列表 */
      getList() {
        this.loading = true;
        listNotice(this.queryParams).then(response => {
            this.noticeList = response.rows;
            this.total = response.total;
            this.loading = false;
            this.downLoadMore = this.total > this.queryParams.pageNum * this.queryParams.pageSize;
          }
        );
      },
      pulldownloadmore() {
        this.queryParams.pageNum = this.queryParams.pageNum + 1;
        this.loading = true;
        listNotice(this.queryParams).then(response => {
            this.noticeList.push(response.rows);
            this.loading = false;
          }
        );
      },
    }
  }
</script>

<style scoped lang="scss">
  .search-page {
    background-size: cover;
  }

  .navbar {
    height: 45px;
    overflow: hidden;
    position: fixed;
    width: 100%;
    z-index: 999;
    background: #1890ff;
    box-shadow: 0 1px 4px rgba(0,21,41,.08);

    .topmenu-container {
      position: absolute;
      left: 50px;
    }

    .home-header-bar {
      text-align: center;
      z-index: 999;
      margin-top: 0.25rem;

      .homeTitle {
        color: #fff;
        font-size: 18px;
        vertical-align: middle;
        i {
          vertical-align: middle;
        }
      }
      .homeRefresh{
        display: inline-block;
        vertical-align: middle;
        float: right;
        background: #1890ff;
        border-color: #1890ff;
        margin-top: -0.25rem;
      }

      .el-button--medium {
        font-size: 25px;
        color: #fff;
      }
    }
  }

  .home {
    .search-icon {
      cursor: pointer;
      font-size: 25px;
      position: absolute;
      vertical-align: middle;
      margin: 6px;
      text-align: center;
    }

    .title {
      margin: 0 auto 30px auto;
      text-align: center;
      font-size: 30px;
      color: #606266;
    }

    .contentNav {
      margin-left: 0.5rem;
      margin-right: 0.5rem;
      padding-top: 45px;
      .noticeContent {
        width: 100%;
        .newsDetail {
          width: 94%;
          display: block;
          position: relative;
          margin: 0 auto;
          padding-bottom: 0.15rem;
        }
        .bottom_line {
          margin-top: 20px;
          border-top: 1px solid #1890ff;
          @media screen and (-webkit-min-device-pixel-ratio: 3) {
            transform: scaleY(0.33333);
          }
          @media screen and (-webkit-min-device-pixel-ratio: 2) {
            transform: scaleY(0.5);
          }
        }
      }
    }

    .pulldownload {
      margin-bottom: 1.3rem;
      width: 100%;
      height: 1.5rem;
      line-height: 1.5rem;
      color: #000;
      font-size: 18px;
      text-align: center;
    }
  }

  .nav {
    width: 100%;
    position: fixed;
    left: 0;
    bottom: 0;
    text-align: center;
    font-size: 10px;
    overflow: hidden;
    z-index: 1000;
    opacity: 1;
    background: #fff;
    box-shadow: 0 0 5px #ccc;
    height: 1.2rem;
    .navItems {
      display: block;
      float: left;
      width: 33%;
      height: 100%;
      color: #999;
      padding-top: 5px;
      .bottom_nav_icon{
        height: 57%;
        margin: 0 auto;
      }
      .icon_1{
        background: url(/bottom_nav/home_dark_icon.png) no-repeat center center;
        background-size: contain;
      }
      .icon_2{
        background: url(/bottom_nav/collect_dark_icon.png) no-repeat center center;
        background-size: contain;
      }
      .icon_3{
        background: url(/bottom_nav/head_dark_icon.png) no-repeat center center;
        background-size: contain;
      }
      .bottom_nav_text{
        height: 43%;
      }
      .bottom_nav_text_on{
        height: 43%;
        color: #1890ff;
      }
    }
  }
</style>
