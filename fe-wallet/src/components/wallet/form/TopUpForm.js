import classes from "./ActionForm.module.css";
import { useDispatch, useSelector } from "react-redux";
import { updateWallet } from "../../../redux/wallet-action";
import { useState } from "react";

function TopUpForm() {
  const { wallet } = useSelector((state) => state.modal);
  const dispatch = useDispatch();
  const [errors, setErrors] = useState();

  const handleSubmit = (event) => {
    event.preventDefault();

    const amount = Number(event.target[0].value).toFixed(2);
    if (!amount || amount <= 0) {
      setErrors((prev) => (prev = { message: "Incorrect Ammount" }));
      return;
    }

    setErrors();
    dispatch(
      updateWallet({
        action: "topUp",
        walletNumber: wallet.walletNumber,
        currency: "USD",
        amount: amount,
      })
    );
  };

  return (
    <form
      onSubmit={handleSubmit}
      method="post"
      className={classes.actions_form}
    >
      <div>
        <h1>Top Up Wallet</h1>
      </div>
      <div>
        <label htmlFor="amount">Amount</label>
        <input name="amount" type="number" step="0.01" id="amount" />
      </div>
      <div>
        <button type="submit">TopUp</button>
      </div>
      {errors && <p>{errors.message}</p>}
    </form>
  );
}

export default TopUpForm;
