import { useDispatch } from "react-redux";
import { showModal } from "../../../redux/modalSlice";
import { availableWalletActions } from "./availableAction";

const WalletActions = (props) => {
  const dispatch = useDispatch();

  const handleShowModal = (walletInfo) => {
    dispatch(showModal(walletInfo));
  };

  return (
    <>
      {availableWalletActions.map((action, index) => (
        <div key={index}>
          <button
            onClick={() =>
              handleShowModal({
                wallet: props.wallet,
                modalType: action.type,
              })
            }
          >
            {action.title}
          </button>
        </div>
      ))}
    </>
  );
};

export default WalletActions;
