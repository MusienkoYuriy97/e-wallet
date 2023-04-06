import Wallets from "./pages/Wallets";
import NewWallet from "./pages/NewWallet";
import RootLayout from "./layout/RootLayout";
import {
  Navigate,
  RouterProvider,
  createBrowserRouter,
} from "react-router-dom";
import ErrorPage from "./pages/ErrorPage";
import { checkAuthLoader, checkIfNoAuthLoader, tokenLoader } from "./util/auth";
import Authentication, {
  action as authenticatAction,
} from "./pages/Authentication";

const router = createBrowserRouter([
  {
    id: "root",
    path: "/wallets",
    element: <RootLayout />,
    errorElement: <ErrorPage />,
    loader: tokenLoader,
    children: [
      { index: true, element: <Wallets />, loader: checkAuthLoader },
      {
        path: "new",
        loader: checkAuthLoader,
        element: <NewWallet />,
      },
      {
        path: "auth",
        element: <Authentication />,
        loader: checkIfNoAuthLoader,
        action: authenticatAction,
      },
    ],
  },
  {
    path: "*",
    element: <Navigate to="/wallets" replace />,
  },
]);

function App() {
  return <RouterProvider router={router} />;
}

export default App;
