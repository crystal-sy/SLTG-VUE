<template>
    <div id="my">
        <div class="my-header" v-if="logined">
            <router-link to="/selfpage" class="my-info">
                <img src="../assets/imgs/head.jpg" alt="" class="my-head-portrait fl">
                <span class="my-name fl">{{userName}}</span>
                <Icon type="ios-arrow-right" size="26" color="#fff" class="fr toMyself"></Icon>
            </router-link>
            <ul class="info-bar clearfix">
                <router-link to="/selfpage" class="info-bar-item">
                    <p>1</p>动态</router-link>
                <router-link to="/care" class="info-bar-item">
                    <p>{{vitality.follow}}</p>关注</router-link>
                <router-link to="/care" class="info-bar-item">
                    <p>{{vitality.fans}}</p>粉丝</router-link>
                <router-link to="/care" class="info-bar-item">
                    <p>{{vitality.visitor}}</p>7天访量</router-link>
            </ul>
        </div>
        <div v-else class="loginBox">
            <h2 class="loginTitle">虚假新闻管理系统</h2>
            <Input class="admin loginInfo" placeholder="请输入登录账号" v-model="admin"></Input>
            <Input class="password loginInfo" placeholder="请输入登录密码" type="password" v-model="password"></Input>
            <br>
            <Button @click="login" @keyup.enter="login" type="ghost" shape="circle" class="submit">登录</Button>
        </div>
        <bottom-nav></bottom-nav>
    </div>
</template>

<script>
    import * as type from '../store/mutation-types.js'
    import {
        mapGetters,
        mapActions
    } from 'vuex'
    import bottomNav from '../components/Bottom-nav.vue'
    export default {
        components: {
            bottomNav
        },
        computed: {
            ...mapGetters([
                'userName',
                'vitality',
                'logined'
            ])
        },
        methods: {
            ...mapActions([
                'setUserInfo',
            ]),
            login() {
                if (this.admin === 'admin' && this.password === 'admin') {
                    this.setUserInfo(this.admin);
                    this.$store.commit(type.LOGOFF, true);
                    this.admin = '';
                    this.password = '';
                } else if (this.admin === '') {
                    this.open('1')
                } else if (this.password === '') {
                    this.open('2')
                } else {
                    this.open('3')
                }
            },
            open(nodesc) {
                if (nodesc === '1') {
                    this.$Notice.open({
                        title: '账号不能为空',
                        duration: 2
                    });
                } else if (nodesc === '2') {
                    this.$Notice.open({
                        title: '密码不能为空',
                        duration: 2
                    });
                } else {
                    this.$Notice.open({
                        title: '账号或密码错误',
                        duration: 2
                    });
                }
            }
        },
        data() {
            return {
                admin: '',
                password: ''
            }
        }
    }
</script>

<style lang="less">@import '../assets/css/border.less';
.my-header {
    height: 5.5rem;
    width: 100%;
    background: rgba(51,51,51,1);
    .my-info {
        height: 4rem;
        display: block;
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
        .info-bar-item {
            float: left;
            width: 25%;
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
    height: 26rem;
    width: 100%;
    text-align: center;
    vertical-align: middle;
    margin-top: 5rem;
    .loginTitle {
        font-size: 20px;
        padding-top: 0.7rem;
        margin-bottom: 0.4rem;
    }
    .loginInfo {
        width: 70%;
        font-size: 18px;
        height: 1.2rem;
    }
    .submit {
        background: #1890ff;
        color: #fff;
        width: 70%;
    }
}
</style>
