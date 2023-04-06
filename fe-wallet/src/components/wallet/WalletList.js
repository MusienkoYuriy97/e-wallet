import classes from "./WalletList.module.css";
import WalletItem from "./WalletItem";
import { useDispatch, useSelector } from "react-redux";
import React, { useEffect } from "react";
import { fetchWallets } from "../../redux/wallet-action";

const WalletList = React.memo(({ currentPage }) => {
  const { items } = useSelector((state) => state.wallets);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchWallets(currentPage));
  }, [dispatch, currentPage]);

  return (
    <section className={classes.wallets}>
      <ul>
        {items.map((wallet) => (
          <WalletItem key={wallet.id} {...wallet} />
        ))}
      </ul>
    </section>
  );
});

export default WalletList;
