<template>
  <div class="search-page home">
    <div class="navbar">
      <div class="home-header-bar">
        <el-button size="medium" icon="el-icon-arrow-left" class="itemLeft" @click="goBack()"/>
        <span class="homeTitle">新闻评论</span>
      </div>
    </div>
    <div v-loading="loading" class="contentNav">
      <div v-for="(item, index) in commentList" class="newsContent">
        <p style="font-size: 16px; font-weight: bold; color: #000;">{{item.commentText}}</p>
        <div>
          <div style=" font-size: 14px; margin-top: 0.6rem; color: #000;">
            <span style="margin-right: 0.2rem;">{{item.userName}}</span> &nbsp;&nbsp;
            <span style="float: right; color: #000;">{{item.commentTime}}</span>
          </div>
        </div>
        <div class="bottom_line"/>
      </div>
    </div>
    <div class="pulldownload" v-show="downLoadMore" @click="pulldownloadmore()">
      点击加载更多
    </div>
  </div>
</template>

<script>
  import { commentList } from "@/api/news/comment";

  export default {
    name: 'comment',
    data() {
      return {
        // 遮罩层
        loading: true,
        // 加载
        downLoadMore: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          newsId: this.$route.query.new_id
        },
        // 评论表格数据
        commentList: null,
      }
    },
    created() {
      this.getList();
    },
    methods: {
      goBack(){
        this.$router.go(-1);
      },
      /** 查询新闻评论列表 */
      getList() {
        this.loading = true;
        commentList(this.queryParams).then(response => {
            this.commentList = response.rows;
            this.total = response.total;
            this.loading = false;
            this.downLoadMore = this.total > this.queryParams.pageNum * this.queryParams.pageSize;
          }
        );
      },
      pulldownloadmore() {
        this.queryParams.pageNum = this.queryParams.pageNum + 1;
        this.loading = true;
        commentList(this.queryParams).then(response => {
            var data = response.rows;
            for (var item in data) {
              this.commentList.push(data[item]);
            }
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
      margin-top: 0.28rem;
      margin-right: 1rem;

      .itemLeft {
        margin-top: -0.28rem;
        display: inline-block;
        vertical-align: middle;
        float: left;
        border-color: #1890ff;
        background-color: #1890ff;
      }

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
      .newsContent {
        width: 100%;

        .newsDetail {
          width: 94%;
          display: block;
          position: relative;
          margin: 0 auto;
          padding-bottom: 0.15rem;
        }

        .bottom_line {
          margin-top: 5px;
          border-top: 1px solid #ccc;
          @media screen and (-webkit-min-device-pixel-ratio: 3) {
            transform: scaleY(0.33333);
          }
          @media screen and (-webkit-min-device-pixel-ratio: 2) {
            transform: scaleY(0.5);
          }
        }
      }
      .detail-title {
        color: #000;
        font-size: 20px;
        font-weight: bold;
      }
      .media_name {
        position: relative;
        margin-top: 0.5rem;
        .avatar_url {
          height: 1.1rem;
          width: 1.1rem;
          border-radius: 50%;
        }
        .name {
          position: absolute;
          font-size: 14px;
          font-weight: bold;
          left: 1.3rem;
          top: 0.1rem;
        }
        .date {
          position: absolute;
          left: 1.3rem;
          top: 0.6rem;
        }
        .newsType {
          position: absolute;
          font-weight: bold;
          font-size: 13px;
          left: 7.5rem;
          top: 0.1rem;
          background-color: #1890ff;
          color: #fff;
          text-align: center;
          border: 2px solid #ccc;
          padding: 0 0.2rem;
          border-radius: 1.4rem;
          margin-right: 0.3rem;
        }
      }
      .contentNews {
        margin-top: 0.5rem;
        font-size: 16px;
        color: #000;
        text-indent: 0.7rem;
        line-height: .8rem;
        margin-bottom: 0.2rem;
        img {
          text-align: center;
          display: block;
          width: 70%;
          margin: 0.2rem auto;
        }
      }
      .keywords {
        margin-top: 0.2rem;
        line-height: 0.8rem;
        span {
          margin-bottom: 0.3rem;
          text-align: center;
          margin-right: 0.3rem;
          display: inline-block;
          border: 1px solid #ccc;
          padding: 0 0.2rem;
          border-radius: 0.4rem;
        }
      }
      .zan{
        text-align: center;
        margin-top: .5rem;
        margin-bottom: .5rem;
        botton{
          padding-right: 1rem;
          color: #000;
        }
      }

      hr{
        height: 1px;
        background: #ccc;
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
</style>
