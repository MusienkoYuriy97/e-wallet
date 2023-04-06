import getSymbolFromCurrency from "currency-symbol-map";

import Card from "../UI/card/Card";
import classes from "./WalletItem.module.css";
import WalletActions from "./actions/WalletActions";

const WalletItem = (props) => {
  const { name, currency, amount, walletNumber } = props;

  return (
    <li className={classes.item}>
      <Card>
        <div>
          <ul>
            <li>Wallet Name: {name}</li>
            <li>Wallet Number: {walletNumber}</li>
            <li>Currency: {currency}</li>
            <li>
              Amount: {getSymbolFromCurrency(currency)}
              {amount}
            </li>
          </ul>
        </div>
        <div className={classes.right}>
          <WalletActions wallet={{ name, walletNumber, currency, amount }} />
        </div>
      </Card>
    </li>
  );
};

export default WalletItem;
