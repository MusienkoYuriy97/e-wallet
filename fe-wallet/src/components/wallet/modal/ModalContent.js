import { TOP_UP, TRANSFER, WITHDRAW } from "../actions/availableAction";
import React from "react";
import WithdrawForm from "../form/WithdrawForm";
import TopUpForm from "../form/TopUpForm";
import TransferForm from "../form/TransferForm";

const ModalContent = ({ modalType }) => {
  let content = "";

  if (modalType === TOP_UP) {
    content = <TopUpForm />;
  }

  if (modalType === WITHDRAW) {
    content = <WithdrawForm />;
  }

  if (modalType === TRANSFER) {
    content = <TransferForm />;
  }
  return content;
};

export default ModalContent;
