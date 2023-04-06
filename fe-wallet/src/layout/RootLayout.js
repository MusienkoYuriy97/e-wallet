import { Outlet, useRouteLoaderData } from "react-router-dom";

import MainNavigation from "../components/MainNavigation";
import { useSelector } from "react-redux";
import Notification from "../components/UI/notification/Notification";

function RootLayout() {
  const { notification } = useSelector((state) => state.ui);
  const token = useRouteLoaderData("root");

  return (
    <>
      {notification && <Notification {...notification} />}
      {token && <MainNavigation />}
      <main>
        <Outlet />
      </main>
    </>
  );
}

export default RootLayout;
