<template>
  <div class="search-page home">
    <div class="navbar">
      <div class="home-header-bar">
        <span class="homeTitle">修改密码</span>
      </div>
    </div>
    <el-form ref="form" :model="user" :rules="rules" label-width="80px" style="padding-top: 6em;padding-right: 0.5em;">
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input v-model="user.oldPassword" placeholder="请输入旧密码" type="password" maxLength="20" />
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="user.newPassword" placeholder="请输入新密码" type="password" maxLength="20" />
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="user.confirmPassword" placeholder="请确认密码" type="password" maxLength="20" />
      </el-form-item>
      <el-form-item style="text-align: center;">
        <el-button type="primary" size="medium" @click="submit">保存</el-button>
      </el-form-item>
    </el-form>
    <div class="nav">
      <div class="navItems" @click="go_home()">
        <div class="bottom_nav_icon icon_1"></div>
        <div class="bottom_nav_text">首页</div>
      </div>
      <div class="navItems">
        <div class="bottom_nav_icon icon_2"></div>
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
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      test: "1test",
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // 表单校验
      rules: {
        oldPassword: [
          { required: true, message: "旧密码不能为空", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    go_home() {
      this.$router.push("/home");
    },
    go_my() {
      this.$router.push("/system/user");
    },
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(
            response => {
              this.msgSuccess("修改成功");
              this.$store.dispatch('LogOut').then(() => {
                location.href = '/index';
              })
            }
          );
        }
      });
    }
  }
};
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
