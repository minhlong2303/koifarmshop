import { createBrowserRouter, RouterProvider } from "react-router-dom";
import LoginPage from "./pages/login";
import RegisterPage from "./pages/register";
import HomePage from "./pages/home";
import Dashboard from "./components/dashboard";
import ManageCategory from "./pages/admin/manage-category";
import Cart from "./pages/Cart";
import Order from "./pages/PlaceOrder";

function App() {
  const router = createBrowserRouter([
    {
      path: "/home",
      element: (
        <div className="app">
          <HomePage />
          
        </div>
      ),
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
      element: <Dashboard />,
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
