import { createSlice } from "@reduxjs/toolkit";

const modalSlice = createSlice({
  name: "modal",
  initialState: {
    showModal: false,
    modalType: null,
    wallet: null,
  },
  reducers: {
    showModal(state, action) {
      state.showModal = true;
      state.modalType = action.payload.modalType;
      state.wallet = action.payload.wallet;
    },
    hideModal(state) {
      state.showModal = false;
      state.modalType = null;
      state.wallet = null;
    },
  },
});

export const { showModal, hideModal } = modalSlice.actions;

export default modalSlice;
