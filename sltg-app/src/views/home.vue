<template>
    <div id="home" class="clearfix">
        <headerBar>
            <div slot="home" class="home-header-bar">
                <span class="logo">新闻管理</span>
                <Icon type="ios-refresh-empty" size="40" color="#fff" class="homeRefresh"></Icon>
            </div>
        </headerBar>
        <ul class="homeNav">
            <li v-for="(item,index) in navbar" :key="index" class="navBarLi">
                <router-link :to="{path:item.url,query:{type:item.type}}">{{item.text}}</router-link>
            </li>
        </ul>
        <ul class="SearchNav">
            <el-input
                    v-model="input"
                    placeholder="请输入内容"
                    @keyup.native.enter="search(input)"
                    class="searchInput fl">
                <Icon type="ios-refresh-empty" size="30"></Icon>
            </el-input>
            <Icon type="ios-search" size="30"></Icon>
        </ul>
        <div v-show="loading" v-loading="loading" element-loading-text="拼命加载中" style="width: 100%" class="loading"></div>
        <transition enter-active-class="bounceInLeft" leave-active-class="bounceOutRight">
            <ul class="newsContent animated" v-show="!loading&&ifReturnMsg">
                <router-link
                        v-for="(val,index) in listCon"
                        :to="{
                    name:'newsDetail',
                    params:
                        {   id:val.tag_id,
                            title:val.title,
                            media_info:val.media_info,
                            media_name:val.media_name,
                            datetime:val.datetime,
                            abstract:val.abstract,
                            image_list:val.image_list,
                            repin_count:val.repin_count,
                            comment_count:val.comment_count,
                            keywords:val.keywords
                        }
                }"
                        class="newsDetail"
                        :key="index"
                >
                    <p class="title">{{val.title}}</p>
                    <div>
                        <div class="bottomInfo clearfix">
                            <span class="writer">{{val.media_name}}</span> &nbsp;&nbsp;
                            <span class="comment_count">评论&nbsp;{{val.comment_count}}</span>
                            <span class="datetime">{{val.datetime.substring(0, 10)}}</span>
                        </div>
                    </div>
                </router-link>
            </ul>
        </transition>
        <div class="pulldownload" v-show="downLoadMore" @click="pulldownloadmore({kind:first || $router.query.type,flag:downLoadMore})">
            点击加载更多
        </div>
        <transition name="bounce" enter-active-class="zoomInLeft" leave-active-class="zoomOutRight">
            <Back-top v-show="top" class="animated bounce">
                <div class="top">
                    <Icon type="android-arrow-up"></Icon>
                </div>
            </Back-top>
        </transition>
        <bottom-nav></bottom-nav>
    </div>
</template>

<script>
    import moment from 'moment'
    import headerBar from '../components/Header-bar.vue'
    import bottomNav from '../components/Bottom-nav.vue'
    import toTop from '../components/To-top.vue'
    import * as type from '../store/mutation-types.js'
    import {
        mapActions,
        mapState,
        mapGetters
    } from 'vuex'
    import axios from 'axios'
    export default {
        components: {
            headerBar,
            bottomNav,
            toTop
        },
        computed: {
            ...mapGetters([
                'newsList',
                'loading',
                'list',
                'ifReturnMsg',
                'oneDetail',
                'routerChange',
                'downLoadMore'
            ]),
            listCon: function() {
                if (this.$route.query.type) {
                    console.info(this.list[this.$route.query.type])
                    return this.list[this.$route.query.type];
                } else {
                    console.info(this.list[this.first])
                    return this.list[this.first]
                }
            },
        },
        beforeRouteUpdate (to, from, next){
            this.$store.commit(type.PULLDOWNBTN, false);
            next();
        },
        methods: {
            ...mapActions([
                'getNews',
                'pulldownloadmore'
            ]),
            handleScroll() {
                this.top = window.document.body.scrollTop > 400;
            },
        },
        mounted() {
            this.getNews({
                kind: this.first,
                flag: this.routerChange
            });
            // this.loading = true;
            const _this = this;
            window.addEventListener('scroll', this.handleScroll);
        },
        watch: {
            '$route': function() {
                this.getNews({
                    kind: this.$route.query.type,
                    flag: this.routerChange
                });
                if (this.routerChange) {
                    this.$store.commit(type.CHANGE_LOADING_STATE, true)
                } else {
                    this.$store.commit(type.CHANGE_LOADING_STATE, false)
                }
                this.first = window.location.search.substring(6);
                this.$store.commit(type.ROUTERCHANGE, true);
            },
        },
        filters: {
            date: function(input) {
                if (!input) {
                    return ''
                }
                var time = moment(input).startOf('minute').fromNow();
                return time;
            }
        },
        data() {
            return {
                input:'',
                navbar: [{
                    text: '新闻库',
                    url: '/home/news',
                    type: 'news'
                },
                    {
                        text: '知识库',
                        url: '/home/knowledge',
                        type: 'knowledge'
                    },
                    {
                        text: '用户新闻',
                        url: '/home/user_news',
                        type: 'user_news'
                    },
                ],
                top: false,
                first: window.location.search.substring(6),
            }
        },
    }
</script>

<style lang="less" scoped rel="styleheet/less">@import "../assets/css/transtion.less";
@import '../assets/css/border.less';
.home-header-bar {
    .logo {
        color: #fff;
        font-size: 18px;
        vertical-align: middle;
        i {
            vertical-align: middle;
        }
    }
    .homeRefresh{
        display: inline-block;
        margin-right: 0.22rem;
        vertical-align: middle;
        float: right;
    }
}
.homeNav {
    width: 100%;
    overflow: hidden;
    overflow-x: auto;
    text-align: center;
    position: fixed;
    left: 0;
    font-size: 0;
    top: 1.2rem;
    background: #f4f5f6;
    font-family: '微软雅黑',serif;
    white-space: nowrap;
    z-index: 999;
    .navBarLi {
        display: inline-block;
        height: 1rem;
        line-height: 1rem;
        width: 3.4rem;
        font-size: 16px;
        a {
            color: #000;
        }
        .router-link-active {
            color: #1890ff;
            font-size: 17px;
            font-weight: bold;
        }
    }
}
.SearchNav {
    width: 100%;
    overflow: hidden;
    overflow-x: auto;
    text-align: center;
    position: fixed;
    left: 0;
    font-size: 0;
    top: 2.2rem;
    background: #f4f5f6;
    font-family: '微软雅黑',serif;
    white-space: nowrap;
    z-index: 999;
    .navBarLi {
        display: inline-block;
        height: 1rem;
        line-height: 1rem;
        width: 1.4rem;
        font-size: 16px;
        a {
            color: #000;
        }
        .router-link-active {
            color: #1890ff;
            font-size: 17px;
            font-weight: bold;
        }
    }
}
::-webkit-scrollbar {
    display: none;
}
.newsContent {
    margin-top: 3.2rem;
    width: 100%;
    .newsDetail {
        width: 94%;
        display: block;
        position: relative;
        margin: 0 auto;
        padding-bottom: 0.15rem;
        .borderBottom(1px,#ccc);
        .title {
            font-size: 16px;
            font-weight: bold;
            color: #000;
            padding-top: 0.2rem;
            padding-bottom: 0.15rem;
        }
        img {
            width: 31.1%;
            margin-right: 0.21rem;
            height: 2.3rem;
        }
        .bottomInfo {
            font-size: 10px;
            margin-top: 0.15rem;
            .writer {
                color: #000;
            }
            .comment_count {
                color: #000;
            }
            .datetime {
                float: right;
                color: #000;
            }
            .avIcon {
                display: inline-block;
                height: 0.4rem;
                width: 0.9rem;
                text-align: center;
                line-height: 0.4rem;
                border-radius: 4px;
                border: 1px solid #39f;
                font-size: 10px;
                margin-right: 0.1rem;
            }
        }
    }
}
.loading {
    margin-top: 5.4rem;
}
.newsLoadError {
    margin: 2.3rem auto;
    font-size: 25px;
    width: 90%;
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
.top {
    position: absolute;
    bottom: 2rem;
    right: 0.15rem;
    background: #d43d3d;
    color: #fff;
    text-align: center;
    border-radius: 50%;
}
.pulldownbtn {
    position: absolute;
    margin: 0 auto;
    left: 50%;
    top: 0.5rem;
    z-index: 1000000;
}
.SearchNav{
    width: 100%;
    .searchIcon{
        display: inline-block;
        padding: 0 .05rem;
        font-size: 13px;
        text-align: center;
        width: 10%;
    }
    .searchInput{
        width: 90%;
        float: left;
    }
}
</style>
