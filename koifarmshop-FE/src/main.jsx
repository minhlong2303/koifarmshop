 
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "./index.css";

import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";
import { ConfigProvider } from "antd";

createRoot(document.getElementById("root")).render(
  <>
    <ConfigProvider
      theme={{
        components: {
          Form: {
            itemMarginBottom: 5,
          },
          Divider: {
            margin: 0,
          },
        },
      }}
    ></ConfigProvider>

    <StrictMode>
      <App></App>
      <ToastContainer></ToastContainer>
    </StrictMode>
  </>
);
