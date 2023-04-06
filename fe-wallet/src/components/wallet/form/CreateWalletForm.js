import classes from "./CreateWalletForm.module.css";
import { useDispatch } from "react-redux";
import { createWallet } from "../../../redux/wallet-action";
import { useState } from "react";

function WalletForm() {
  const dispatch = useDispatch();
  const [errors, setErrors] = useState();

  const handleSubmit = (event) => {
    event.preventDefault();

    const name = event.target[0].value;
    if (!name || name.trim().length < 2) {
      setErrors(
        (prev) => (prev = { message: "Name shouldn't have length less than 2" })
      );
      return;
    }

    const currency = event.target[1].value;
    if (!currency || currency !== "USD") {
      setErrors((prev) => (prev = { message: "Only USD currency allowed" }));
      return;
    }
    setErrors();
    dispatch(
      createWallet({
        name: event.target[0].value,
        currency: event.target[1].value,
      })
    );
  };

  return (
    <form onSubmit={handleSubmit} method="post" className={classes.form}>
      <div>
        <p>
          <label htmlFor="name">Wallet Name</label>
          <input id="name" type="text" name="name" required />
        </p>
        <p>
          <label htmlFor="currency">Currency</label>
          <select name="currency" id="currency">
            <option value="USD">USD</option>
          </select>
        </p>
        <div className={classes.actions}>
          <button type="submit">Save</button>
        </div>
      </div>
      {errors && <p>{errors.message}</p>}
    </form>
  );
}

export default WalletForm;
