import classes from "./ActionForm.module.css";
import { useDispatch, useSelector } from "react-redux";
import { transfer } from "../../../redux/wallet-action";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { hideModal } from "../../../redux/modalSlice";

function TransferForm() {
  const { wallet } = useSelector((state) => state.modal);
  const dispatch = useDispatch();

  const [errors, setErrors] = useState();
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const receiver = event.target[0].value;
    const amount = Number(event.target[1].value).toFixed(2);
    if (!amount || amount <= 0) {
      setErrors((prev) => (prev = { message: "Incorrect Ammount" }));
      return;
    }
    if (wallet.amount - amount < 0.01) {
      setErrors((prev) => (prev = { message: "Not enough money for sending" }));
      return;
    }
    if (!receiver || receiver.length !== 7) {
      setErrors(
        (prev) => (prev = { message: "Receiver number isn't correct!" })
      );
      return;
    }

    const response = await transfer({
      action: "withdraw",
      walletNumber: wallet.walletNumber,
      currency: "USD",
      receiverWalletNumber: receiver,
      amount,
    });
    if (response.httpStatus > 299) {
      setErrors((prev) => (prev = { message: response.errors.join(". ") }));
      return;
    }
    dispatch(hideModal());
    navigate("/");
  };

  return (
    <form
      onSubmit={handleSubmit}
      method="post"
      className={classes.actions_form}
    >
      <div>
        <h1>Transfer Money</h1>
      </div>
      <div>
        <label htmlFor="receiver">Wallet Number of Receiver</label>
        <input name="receiver" type="text" id="receiver" required />
      </div>
      <div>
        <label htmlFor="amount">Amount</label>
        <input name="amount" type="number" step="0.01" id="amount" />
      </div>
      <div>
        <button type="submit">Transfer</button>
      </div>
      {errors && <p>{errors.message}</p>}
    </form>
  );
}

export default TransferForm;
