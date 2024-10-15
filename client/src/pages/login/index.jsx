/* eslint-disable react/jsx-no-undef */
/* eslint-disable no-unused-vars */
import React from "react";
import AuthenTemplate from "../../components/authen-template";
import { Button, Divider, Form, Input } from "antd";
import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";
import { googleProvider } from "../../config/firebase";
import "./index.css";
import { FcGoogle } from "react-icons/fc";
import { Link, useNavigate } from "react-router-dom";
import api from "../../config/axios";
import { toast } from "react-toastify";
import { useDispatch } from "react-redux";
import { login } from "../../redux/features/userSlice";

function LoginPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const handleLoginGoogle = () => {
    const auth = getAuth();
    signInWithPopup(auth, googleProvider)
      .then((result) => {
        // This gives you a Google Access Token. You can use it to access the Google API.
        const credential = GoogleAuthProvider.credentialFromResult(result);
        const token = credential.accessToken;
        // The signed-in user info.
        const user = result.user;

        // IdP data available using getAdditionalUserInfo(result)
        // ...
      })
      .catch((error) => {
        console.log(error);
        // Handle Errors here.
        const errorCode = error.code;
        const errorMessage = error.message;
        // The email of the user's account used.
        const email = error.customData.email;
        // The AuthCredential type that was used.
        const credential = GoogleAuthProvider.credentialFromError(error);
        // ...
      });
  };

  const handleLogin = async (values) => {
    try {
      const response = await api.post("login", values);
      console.log(response);
      dispatch(login(response.data));
      const { role, token } = response.data;
      toast.success("Đăng nhập thành công!");
      localStorage.setItem("token", token);
      if (role === "MANAGER") {
        navigate("/dashboard");
      } else {
        navigate("/");
      }
    } catch (err) {
      toast.error(err.response.data);
    }
  };
  //
  return (
    <div className="form">
      <AuthenTemplate>
        <Form
          labelCol={{
            span: 24,
          }}
          size="large"
          onFinish={handleLogin}
        >
          <h1 className="title">Đăng nhập</h1>
          <Form.Item
            label="Số điện thoại"
            name="username"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập số điện thoại hoặc tên đăng nhập!",
              },
            ]}
          >
            <Input placeholder="Số điện thoại và tên đăng nhập" />
          </Form.Item>
          <Form.Item
            label="Mật khẩu"
            name="password"
            rules={[
              {
                required: true,
                message: "Vui lòng nhập mật khẩu!",
              },
            ]}
          >
            <Input.Password placeholder="Mật khẩu" />
          </Form.Item>

          <div className="forget-link">
            <Link to="">Quên mật khẩu</Link>
          </div>
          <div className="login">
            <Button type="primary" htmlType="submit" className="login-button">
              Đăng nhập
            </Button>
            <Divider
              style={{
                borderColor: "gray",
              }}
            >
              Hoặc
            </Divider>
            <Button className="google-button" onClick={handleLoginGoogle}>
              <FcGoogle size={30} />
              Đăng nhập với Google
            </Button>
          </div>
          <div className="signup-link">
            <Link to="/register">Bạn chưa có tài khoản? Đăng kí </Link>
          </div>
        </Form>
      </AuthenTemplate>
    </div>
  );
}

export default LoginPage;
