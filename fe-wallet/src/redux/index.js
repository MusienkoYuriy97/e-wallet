import { configureStore } from "@reduxjs/toolkit";
import walletsSlice from "./walletsSlice";
import modalSlice from "./modalSlice";
import uiSlice from "./uiSlice";

const store = configureStore({
  reducer: {
    ui: uiSlice.reducer,
    wallets: walletsSlice.reducer,
    modal: modalSlice.reducer,
  },
});

export default store;
