/* eslint-disable no-unused-vars */
import AuthenTemplate from "../../components/authen-template";
import { Button, Divider, Form, Input } from "antd";
import React from "react";
import "./index.css";
import { getAuth, signInWithPopup, GoogleAuthProvider } from "firebase/auth";
import { googleProvider } from "../../config/firebase";
import { FcGoogle } from "react-icons/fc";
import { Link, useNavigate } from "react-router-dom";
import api from "../../config/axios";
import { toast } from "react-toastify";

function RegisterPage() {
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
  const navigate = useNavigate();

  const handleRegister = async (values) => {
    //submit xuong be
    try {
      values.role = "MANAGER";
      const response = await api.post("register", values);
      toast.success("Đăng kí thành công!");
      navigate("/login");
    } catch (err) {
      //console.log
      toast.error(err.response.data);
    }
  };
  return (
    <div>
      <AuthenTemplate>
        <Form
          labelCol={{
            span: 24,
          }}
          size="large"
          onFinish={handleRegister}
        >
          <h1 className="title">Đăng kí</h1>

          <Form.Item
            label="Tên tài khoản"
            name="username"
            rules={[
              { required: true, message: "Vui lòng nhập tên tài khoản!" },
              {
                max: 30,
                message: "Tên tài khoản không được vượt quá 30 ký tự!",
              },
              {
                pattern: /^[a-zA-Z0-9]+$/,
                message: "Tên tài khoản chỉ được bao gồm chữ cái và số!",
              },
            ]}
          >
            <Input placeholder="Tên tài khoản" />
          </Form.Item>
          <div className="form-row">
            <Form.Item
              label="Họ"
              name="firstName"
              rules={[{ required: true, message: "Vui lòng nhập họ!" }]}
            >
              <Input placeholder="Họ" />
            </Form.Item>

            <Form.Item
              label="Tên"
              name="lastName"
              rules={[{ required: true, message: "Vui lòng nhập tên!" }]}
            >
              <Input placeholder="Tên" />
            </Form.Item>
          </div>

          <Form.Item
            label="Số điện thoại"
            name="phone"
            rules={[
              { required: true, message: "Vui lòng nhập số điện thoại!" },
              {
                pattern: /^((\+84)|0)([1-9]{1}[0-9]{8})$/,
                message: "Số điện thoại không hợp lệ!",
              },
            ]}
          >
            <Input placeholder="Số điện thoại" />
          </Form.Item>

          <Form.Item label="Email" name="email">
            <Input placeholder="Email" />
          </Form.Item>

          <Form.Item
            label="Mật khẩu"
            name="password"
            rules={[
              { required: true, message: "Vui lòng nhập mật khẩu!" },
              {
                min: 6,
                message: "Mật khẩu phải có ít nhất 6 kí tự!",
              },
            ]}
            hasFeedback
          >
            <Input.Password placeholder="Mật khẩu" />
          </Form.Item>

          <Form.Item
            label="Xác nhận lại mật khẩu"
            name="confirmPassword"
            dependencies={["password"]}
            hasFeedback
            rules={[
              { required: true, message: "Vui lòng xác nhận lại mật khẩu!" },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue("password") === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error("Mật khẩu không khớp!"));
                },
              }),
            ]}
          >
            <Input.Password placeholder="Xác nhận lại mật khẩu" />
          </Form.Item>

          <Button type="primary" htmlType="submit" className="register-button">
            Đăng kí
          </Button>
          <Divider
            className="solid"
            style={{
              borderColor: "gray",
            }}
          >
            Hoặc
          </Divider>
          <Button className="google-button" onClick={handleLoginGoogle}>
            <FcGoogle size={30} />
            Đăng kí với Google
          </Button>
          <div className="signin-link">
            <Link to="/login">Bạn đã có tài khoản? Đăng nhập </Link>
          </div>
        </Form>
      </AuthenTemplate>
    </div>
  );
}

export default RegisterPage;
