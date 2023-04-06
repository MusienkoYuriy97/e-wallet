import { createSlice } from "@reduxjs/toolkit";

const walletsSlice = createSlice({
  name: "wallets",
  initialState: {
    total: 0,
    items: [],
  },
  reducers: {
    loadWallets(state, action) {
      state.items = action.payload.items || [];
      state.total = action.payload.total || 0;
    },
    updateWallet(state, action) {
      const wallet = state.items.find(
        (wallet) => wallet.walletNumber === action.payload.walletNumber
      );
      wallet.amount = action.payload.amount;
    },
  },
});

export const walletsActions = walletsSlice.actions;

export default walletsSlice;
