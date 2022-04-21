<template>
  <div>
    <div class="my-header">
      <div class="my-info">
        <div class="user-info-head fl">
          <img v-bind:src="options.img" class="img-circle img-lg" />
        </div>
        <div class="my-name fl">
          <span>{{ user.userName }}</span>
        </div>
      </div>
      <ul class="info-bar clearfix">
        <div class="info-bar-item">
          <p style="margin-top: 0; margin-bottom: 0;">106</p>
          <p style="margin-top: 5px; margin-bottom: 0;">评论</p>
        </div>
        <div class="info-bar-item">
          <p style="margin-top: 0; margin-bottom: 0;">14</p>
          <p style="margin-top: 5px; margin-bottom: 0;">收藏</p>
        </div>
      </ul>
    </div>
    <div class="tipItems" @click="notice()">
      消息通知
      <el-button size="medium" icon="el-icon-arrow-right" class="itemRight"/>
    </div>
    <div class="tipItems" @click="resetPwd()">
      账户设置
      <el-button size="medium" icon="el-icon-arrow-right" class="itemRight"/>
    </div>
    <div class="tipItems" style="text-align: center;margin-top: 7rem;color:#fff;background-color: #1890ff;"
         @click="logout()">
      退出登录
    </div>
    <div class="nav">
      <div class="navItems" @click="go_home()">
        <div class="bottom_nav_icon icon_1"></div>
        <div class="bottom_nav_text">首页</div>
      </div>
      <div class="navItems" @click="go_collect()">
        <div class="bottom_nav_icon icon_2"></div>
        <div class="bottom_nav_text">收藏</div>
      </div>
      <div class="navItems">
        <div class="bottom_nav_icon icon_3"></div>
        <div class="bottom_nav_text_on">我的</div>
      </div>
    </div>
  </div>
</template>

<script>
  import userAvatar from "./userAvatar";
  import userInfo from "./userInfo";
  import { getUserProfile } from "@/api/system/user";
  import store from "@/store";

  export default {
    name: "Profile",
    components: { userAvatar, userInfo },
    data() {
      return {
        user: {},
        roleGroup: {},
        postGroup: {},
        activeTab: "userinfo",
        options: {
          img: store.getters.avatar, //裁剪图片的地址
        },
      };
    },
    created() {
      this.getUser();
    },
    methods: {
      go_home() {
        this.$router.push("/home");
      },
      go_collect() {
        this.$router.push("/news/table");
      },
      notice() {
        this.$router.push("/user/notice");
      },
      resetPwd() {
        this.$router.push("/user/resetPwd");
      },
      getUser() {
        getUserProfile().then(response => {
          this.user = response.data;
          this.roleGroup = response.roleGroup;
          this.postGroup = response.postGroup;
        });
      },
      async logout() {
        this.$confirm('确定注销并退出系统吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$store.dispatch('LogOut').then(() => {
            location.href = '/index';
          })
        }).catch(() => {});
      }
    }
  };
</script>

<style scoped lang="scss">
.my-header {
  height: 5.5rem;
  width: 100%;
  background: rgba(51,51,51,1);
  ul {
    padding-inline-start: 0;
  }
  .my-info {
    height: 3rem;
    display: block;
    .user-info-head {
      position: relative;
      display: inline-block;
      height: 120px;
    }
    .my-head-portrait {
      height: 1.6rem;
      width: 1.6rem;
      border-radius: 50%;
      margin-top: 1.2rem;
      margin-left: 1rem;
    }
    .my-name {
      font-size: 18px;
      font-weight: bold;
      color: #ffd;
      margin-top: 1.6rem;
      margin-left: 0.7rem;
    }
    .toMyself {
      margin-top: 1.6rem;
      margin-right: 0.5rem;
    }
  }
  .info-bar {
    height: 1.5rem;
    margin-block-start: 3em;
    .info-bar-item {
      float: left;
      width: 50%;
      height: 1.2rem;
      box-sizing: border-box;
      border-left: 1px solid #666;
      text-align: center;
      font-size: 13px;
      color: #777;
      &:first-child {
        border: 0;
      }
      p {
        font-size: 16px;
        color: #ffd;
      }
    }
  }
}
.loginBox {
  height: 5.5rem;
  width: 100%;
  background: #d43d3d;
  text-align: center;
  color: #fff;
  .loginTitl {
    font-size: 20px;
    color: #fff;
    padding-top: 0.7rem;
    margin-bottom: 0.4rem;
  }
  .loginInfo {
    width: 70%;
    font-size: 18px;
    height: 1.2rem;
  }
  .submit {
    color: #fff;
    width: 70%;
  }
}
.midBar {
  height: 1.6rem;
  position: relative;
  &::after {
    content: "";
    box-sizing: border-box;
    position: absolute;
    width: 100%;
    left: 0;
    bottom: 0;
    border-top: 1px solid #ccc;
    @media screen and (-webkit-min-device-pixel-ratio: 3) {
      transform: scaleY(0.33333);
    }
    @media screen and (-webkit-min-device-pixel-ratio: 2) {
      transform: scaleY(0.5);
    }
  }
  .isLi {
    width: 50%;
    text-align: center;
    margin-top: 0.2rem;
    box-sizing: border-box;
    font-size: 13px;
    color: #000;
  }
  .collect {
    position: relative;
    display: block;
    &::after {
      content: "";
      box-sizing: border-box;
      position: absolute;
      height: 100%;
      right: 0;
      top: 0;
      border-right: 1px solid #bbb;
      @media screen and (-webkit-min-device-pixel-ratio: 3) {
        transform: scaleY(0.33333);
      }
      @media screen and (-webkit-min-device-pixel-ratio: 2) {
        transform: scaleY(0.5);
      }
    }
  }
}
.tipItems {
  display: block;
  height: 1.1rem;
  width: 100%;
  margin-top: 0.3rem;
  color: #000;
  font-size: 16px;
  line-height: 1.1rem;
  font-weight: bold;
  position: relative;
  padding-left: 0.3rem;
  &::before {
    content: "";
    box-sizing: border-box;
    position: absolute;
    width: 100%;
    left: 0;
    top: 0;
    border-top: 1px solid #ccc;
    @media screen and (-webkit-min-device-pixel-ratio: 3) {
      transform: scaleY(0.33333);
    }
    @media screen and (-webkit-min-device-pixel-ratio: 2) {
      transform: scaleY(0.5);
    }
  }

  &::after {
    content: "";
    box-sizing: border-box;
    position: absolute;
    width: 100%;
    left: 0;
    bottom: 0;
    border-top: 1px solid #ccc;
    @media screen and (-webkit-min-device-pixel-ratio: 3) {
      transform: scaleY(0.33333);
    }
    @media screen and (-webkit-min-device-pixel-ratio: 2) {
      transform: scaleY(0.5);
    }
  }
  .toSetup{
    margin-right: .3rem;
    margin-top: .2rem;
  }

  .itemRight {
    display: inline-block;
    vertical-align: middle;
    float: right;
    border-color: #ffffff;
  }

  .el-button--medium {
    padding: 14px 20px;
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

<style>
  .el-message-box {
    width: 360px;
  }
</style>
