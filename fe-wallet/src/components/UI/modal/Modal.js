import React from "react";
import classes from "./Modal.module.css";

const Modal = ({ handleClose, show, children }) => {
  const showHideClassName = show
    ? `${classes.modal} ${classes.displayblock}`
    : `${classes.modal} ${classes.displaynone}`;

  return (
    <div className={showHideClassName}>
      {show && (
        <section className={classes.modalmain}>
          {children}
          <button type="button" onClick={handleClose}>
            Close
          </button>
        </section>
      )}
    </div>
  );
};

export default Modal;
