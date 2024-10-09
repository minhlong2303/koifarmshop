import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "./index.css";

import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from "react-toastify";
import { ConfigProvider } from "antd";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { persistor, store } from "./redux/store.js";

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

    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <App></App>
        <ToastContainer></ToastContainer>
      </PersistGate>
    </Provider>
  </>
);
