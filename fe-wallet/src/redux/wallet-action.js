import { getAuthToken } from "../util/auth";
import { walletsActions } from "./walletsSlice";
import { uiActions } from "./uiSlice";
import { baseUrl } from "../const";

export const fetchWallets = (pageNumber, pageSize = 5) => {
  return async (dispatch) => {
    const fetchRequest = async () => {
      let response = await fetch(
        `${baseUrl}/wallets?page=${pageNumber - 1}&size=${pageSize}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + getAuthToken(),
          },
        }
      );

      if (!response || !response.ok) {
        throw new Error("Fetching data failed");
      }

      const data = await response.json();
      return data;
    };

    try {
      const response = await fetchRequest();
      dispatch(
        walletsActions.loadWallets({
          total: response.totalElements,
          items: response.content,
        })
      );
    } catch (error) {
      dispatch(
        uiActions.showNotification({
          status: "error",
          title: "Error!",
          message: error.message,
        })
      );
    }
  };
};

export const updateWallet = (formData) => {
  return async (dispatch) => {
    try {
      const response = await walletActionRequest(formData);
      dispatch(
        walletsActions.updateWallet({
          walletNumber: formData.walletNumber,
          amount: response.amount,
        })
      );
    } catch (error) {
      console.log(error);
    }
  };
};

const walletActionRequest = async (formData) => {
  const response = await fetch(`${baseUrl}/wallets/${formData.action}`, {
    method: "PUT",
    body: JSON.stringify({
      walletNumber: formData.walletNumber,
      amount: formData.amount,
      currency: formData.currency,
    }),
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + getAuthToken(),
    },
  });
  if (!response.ok) {
    throw new Error("Sending data failed");
  }
  const data = await response.json();
  return data;
};

export const createWallet = (walletFormData) => {
  return async (dispatch) => {
    const sendRequest = async () => {
      const response = await fetch(`${baseUrl}/wallets`, {
        method: "POST",
        body: JSON.stringify(walletFormData),
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + getAuthToken(),
        },
      });

      if (!response.ok) {
        throw new Error(
          "Cannot create new wallet. Please check if wallet name is unique."
        );
      }
    };

    try {
      await sendRequest();
      dispatch(
        uiActions.showNotification({
          status: "success",
          title: "Success",
          message: "New Wallet was created succesfully!",
        })
      );
    } catch (error) {
      dispatch(
        uiActions.showNotification({
          status: "error",
          title: "Error!",
          message: error.message,
        })
      );
    }
  };
};

export const transfer = async (formData) => {
  const response = await fetch(`${baseUrl}/wallets/transfer`, {
    method: "POST",
    body: JSON.stringify({
      walletNumber: formData.walletNumber,
      amount: formData.amount,
      currency: formData.currency,
      receiverWalletNumber: formData.receiverWalletNumber,
    }),
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + getAuthToken(),
    },
  });
  if (!response.ok) {
    const data = await response.json();
    return data;
  }
  return await response;
};
