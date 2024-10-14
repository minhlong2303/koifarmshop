import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
} from "react-router-dom";
import LoginPage from "./pages/login";
import RegisterPage from "./pages/register";
import HomePage from "./pages/home";
import Dashboard from "./components/dashboard";
import ManageCategory from "./pages/admin/manage-category";
import Cart from "./pages/Cart";
import Order from "./pages/PlaceOrder";
import { useSelector } from "react-redux";
import { toast } from "react-toastify";
import Layout from "./components/layout";

function App() {
  const ProtectRouteAuth = ({ children }) => {
    const user = useSelector((store) => store.user);
    console.log(user);

    if (user && user?.role === "ADMIN") {
      return children;
    }
    toast.error("Bạn không có quyền truy cập!");
    return <Navigate to={"/login"} />;
  };
  const router = createBrowserRouter([
    {
      path: "",
      element: <Layout />,
      children: [
        {
          path: "",
          element: <HomePage />,
        },
      ],
    },

    {
      path: "login",
      element: (
        <div>
          <LoginPage />
        </div>
      ),
    },
    {
      path: "register",
      element: (
        <div>
          <RegisterPage />
        </div>
      ),
    },

    {
      path: "dashboard",
      element: (
        <ProtectRouteAuth>
          <Dashboard />
        </ProtectRouteAuth>
      ),
      children: [
        {
          path: "category",
          element: <ManageCategory />,
        },
      ],
    },
    {
      path: "cart",
      element: (
        <div>
          <Cart />
        </div>
      ),
    },
    {
      path: "order",
      element: (
        <div>
          <Order />
        </div>
      ),
    },
  ]);

  return <RouterProvider router={router} />;
}

export default App;
