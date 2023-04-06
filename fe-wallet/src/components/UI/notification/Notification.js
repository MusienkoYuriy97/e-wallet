import { useDispatch } from "react-redux";
import classes from "./Notification.module.css";
import { uiActions } from "../../../redux/uiSlice";

const Notification = (props) => {
  let specialClasses = "";

  const dispatch = useDispatch();

  if (props.status === "error") {
    specialClasses = classes.error;
  }
  if (props.status === "success") {
    specialClasses = classes.success;
  }

  const cssClasses = `${classes.notification} ${specialClasses}`;

  const hideNotification = () => {
    dispatch(uiActions.hideNotification());
  };

  return (
    <section className={cssClasses}>
      <h2>{props.message}</h2>
      <button onClick={hideNotification}>Cancel</button>
    </section>
  );
};

export default Notification;
