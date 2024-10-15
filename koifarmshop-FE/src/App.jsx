import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
} from "react-router-dom";
import LoginPage from "./pages/login";
import RegisterPage from "./pages/register";
import Dashboard from "./components/dashboard";
import Order from "./pages/PlaceOrder";
import ManageServiceGroup from "./pages/admin/manage-service-group";
import ManageKoi from "./pages/admin/manage-koi";
import ManageKoiSpecies from "./pages/admin/manage-KoiSpecies";
import Test from "./pages/test";
import { toast } from "react-toastify";
import { useSelector } from "react-redux";
import Layout from "./components/Layout";
import ShopPage from "./pages/shop";
import ContactPage from "./pages/Contact/contact";
import AboutUsPage from "./pages/AboutUsPage/AboutUsPage";
import CartPage from "./pages/Cart";
import ManageUsers from "./pages/admin/manage-users/ManageUsers";
import HomePage from "./pages/home/HomePage";

function App() {
  const ProtectRouteAuth = ({ children }) => {
    const user = useSelector((store) => store.user);
    console.log(user);
    if (user && user?.role === "MANAGER") {
      return children;
    }
    toast.error("You are not allowed to access this");
    return <Navigate to={"/login"}></Navigate>;
  };
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Layout></Layout>,
      children: [
        {
          path: "/",
          element: <HomePage></HomePage>,
        },
        {
          path: "check-out",
          element: <HomePage></HomePage>,
        },
        {
          path: "shop",
          element: <ShopPage></ShopPage>,
        },
        {
          path: "contact",
          element: <ContactPage></ContactPage>,
        },
        {
          path: "about-us",
          element: <AboutUsPage></AboutUsPage>,
        },
        {
          path: "cart",
          element: <CartPage></CartPage>,
        },
      ],
    },
    {
      path: "test",
      element: <Test />,
    },
    {
      path: "login",
      element: <LoginPage />,
    },
    {
      path: "register",
      element: <RegisterPage />,
    },

    {
      path: "dashboard",
      element: (
        // <ProtectRouteAuth>
        //   <Dashboard></Dashboard>
        // </ProtectRouteAuth>
        <Dashboard></Dashboard>
      ),
      children: [
        {
          path: "koi",
          element: <ManageKoi />,
        },
        {
          path: "KoiSpecies",
          element: <ManageKoiSpecies />,
        },
        {
          path: "service",
          element: <ManageServiceGroup />,
        },
        {
          path: "users",
          element: <ManageUsers />,
        },
      ],
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
