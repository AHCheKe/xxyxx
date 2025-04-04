<template>
  <div class="login">
    <div class="login-wrap">
      <div class="login-left hidden-sm-and-down">
        <p class="login-left-title">陪诊小程序后台管理</p>
      </div>

      <div class="login-right">
        <div class="login-right-main">
          <h4 class="login-right-main-title">登录</h4>
          <el-form ref="formRef" label-width="80px" :status-icon="true" :model="login" :rules="rules" @keyup.enter="onLogin">
            <el-form-item label-width="0" prop="mobile" style="margin-bottom: 30px;">
              <el-input v-model="login.mobile" placeholder="用户名" prefix-icon="user" autocomplete="off" size="large"></el-input>
            </el-form-item>
            <el-form-item label-width="0" prop="pwd">
              <el-input placeholder="密码" v-model="login.pwd" prefix-icon="lock" autocomplete="off" size="large" show-password></el-input>
            </el-form-item>
            <el-form-item label-width="0">
              <el-button type="primary" size="small" :disabled="state.loading" @click="onLogin" class="login-right-main-btn"> 登录 </el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import { CacheToken } from "@/constants/cacheKey";
import baseService from "@/service/baseService";
import { setCache } from "@/utils/cache";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";

const router = useRouter();

const state = reactive({
  loading: false
});

const login = reactive({ mobile: "", pwd: "" });

const formRef = ref();

const rules = ref({
  mobile: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  pwd: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});

/**
 * 登录
 */
const onLogin = () => {
  formRef.value.validate((valid: boolean) => {
    if (valid) {
      state.loading = true;
      baseService
        .post("/admin/login", login)
        .then((res) => {
          state.loading = false;
          if (res.code === 200 && res.success) {
            setCache(CacheToken, res.data);
            ElMessage.success("登录成功");
            router.push("/service/order/list");
            // router.push({path:'/sys/user',query:{}})
          } else {
            ElMessage.error(res.msg);
          }
        }).catch((err) => {
          console.error(err)
          state.loading = false;
        });
    }
  });
};
</script>

<style lang="less" scoped>
.login {
  width: 100vw;
  height: 100vh;
  background: #2a55c8;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  .login-wrap {
    .login-left {
      background-color: #4a8ffe !important;
    }
  }

  @media only screen and (max-width: 992px) {
    .login-wrap {
      width: 96% !important;
    }

    .login-right {
      width: 100% !important;
    }
  }

  &-wrap {
    margin: 0 auto;
    width: 1000px;
    box-shadow: -4px 5px 10px rgba(0, 0, 0, 0.4);
    animation-duration: 1s;
    animation-fill-mode: both;
    border-radius: 5px;
    overflow: hidden;
  }

  &-left {
    justify-content: center;
    flex-direction: column;
    color: #fff;
    float: left;
    width: 50%;

    &-title {
      text-align: center;
      color: #fff;
      font-weight: 300;
      letter-spacing: 2px;
      font-size: 32px;
    }
  }

  &-right {
    border-left: none;
    color: #fff;
    background-color: #fff;
    width: 50%;
    float: left;

    &-main {
      margin: 0 auto;
      width: 65%;

      &-title {
        color: #333;
        margin-bottom: 40px;
        font-weight: 500;
        font-size: 24px;
        text-align: center;
        letter-spacing: 4px;
      }

      &-lang .iconfont {
        font-size: 20px;
        color: #606266;
        font-weight: 800;
        width: 20px;
        height: 20px;
      }

      .el-input__inner {
        border-width: 0;
        border-radius: 0;
        border-bottom: 1px solid #dcdfe6;
      }

      &-code {
        width: 100%;

        .el-space__item:first-child {
          flex: 1;
        }
      }

      &-btn {
        width: 100%;
        height: 45px;
        font-size: 18px !important;
        letter-spacing: 2px;
        font-weight: 300 !important;
        cursor: pointer;
        margin-top: 30px;
        font-family: neo, sans-serif;
        transition: 0.25s;
      }
    }
  }

  .login-footer {
    text-align: center;
    position: absolute;
    bottom: 0;
    padding: 20px;
    color: rgba(255, 255, 255, 0.6);

    p {
      margin: 10px 0;
    }

    a {
      padding: 0 5px;
      color: rgba(255, 255, 255, 0.6);

      &:focus,
      &:hover {
        color: #fff;
      }
    }
  }

  &-left,
  &-right {
    position: relative;
    min-height: 500px;
    align-items: center;
    display: flex;
  }

  @keyframes animate-down {
    0%,
    60%,
    75%,
    90%,
    to {
      animation-timing-function: cubic-bezier(0.215, 0.61, 0.355, 1);
    }

    0% {
      opacity: 0;
      transform: translate3d(0, -3000px, 0);
    }

    60% {
      opacity: 1;
      transform: translate3d(0, 25px, 0);
    }

    75% {
      transform: translate3d(0, -10px, 0);
    }

    90% {
      transform: translate3d(0, 5px, 0);
    }

    to {
      transform: none;
    }
  }

  .animate-down {
    animation-name: animate-down;
  }
}
</style>
