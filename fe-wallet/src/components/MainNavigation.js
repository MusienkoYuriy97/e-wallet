import { Link } from "react-router-dom";

import classes from "./MainNavigation.module.css";

function MainNavigation() {
  const linkGeneral = (to, title) => {
    return (
      <Link reloadDocument to={to}>
        {title}
      </Link>
    );
  };

  return (
    <header className={classes.header}>
      <nav>
        <ul className={classes.list}>
          <li>{linkGeneral("/wallets", "Wallets")}</li>
          <li>{linkGeneral("/wallets/new", "Create Wallet")}</li>
        </ul>
      </nav>
    </header>
  );
}

export default MainNavigation;
