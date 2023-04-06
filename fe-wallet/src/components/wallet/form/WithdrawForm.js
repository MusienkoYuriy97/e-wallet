import classes from "./ActionForm.module.css";
import { useDispatch, useSelector } from "react-redux";
import { updateWallet } from "../../../redux/wallet-action";
import { useState } from "react";

function WithdrawForm() {
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

    if (wallet.amount - amount < 0.01) {
      setErrors((prev) => (prev = { message: "Not enough money to withdraw" }));
      return;
    }

    setErrors();
    dispatch(
      updateWallet({
        action: "withdraw",
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
        <h1>Withdraw Money</h1>
      </div>
      <div>
        <label htmlFor="amount">Amount</label>
        <input name="amount" type="number" step="0.01" id="amount" />
      </div>
      <div>
        <button type="submit">Withdraw</button>
      </div>
      {errors && <p>{errors.message}</p>}
    </form>
  );
}

export default WithdrawForm;
