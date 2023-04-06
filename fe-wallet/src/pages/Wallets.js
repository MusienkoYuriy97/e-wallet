import PaginationBlock from "../components/UI/page/pagination/PaginationBlock";
import { useCallback, useState } from "react";
import WalletList from "../components/wallet/WalletList";
import { useDispatch, useSelector } from "react-redux";
import { hideModal } from "../redux/modalSlice";
import Modal from "../components/UI/modal/Modal";
import ModalContent from "../components/wallet/modal/ModalContent";

function Wallets() {
  const dispatch = useDispatch();
  const [currentPage, setCurrentPage] = useState(1);
  const { total } = useSelector((state) => state.wallets);
  const { showModal, modalType } = useSelector((state) => state.modal);

  const onHideModal = useCallback(() => {
    dispatch(hideModal());
  }, [dispatch]);

  return (
    <>
      {
        <Modal show={showModal} handleClose={onHideModal}>
          <ModalContent modalType={modalType} />
        </Modal>
      }
      <PaginationBlock
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        total={total}
      />
      <WalletList currentPage={currentPage} />
    </>
  );
}

export default Wallets;
