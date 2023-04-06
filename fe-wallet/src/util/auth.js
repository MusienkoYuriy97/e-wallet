import jwtDecode from "jwt-decode";
import { redirect } from "react-router-dom";

export function getTokenDuration(token) {
  const decodedToken = jwtDecode(token);
  const expirationDate = new Date(decodedToken.exp);
  const now = new Date();
  const duration = expirationDate * 1000 - now.getTime();
  return duration;
}

export function getAuthToken() {
  const token = localStorage.getItem("token");

  if (!token) {
    return null;
  }

  const tokenDuration = getTokenDuration(token);

  if (tokenDuration < 0) {
    localStorage.removeItem("token");
    return null;
  }

  return token;
}

export function tokenLoader() {
  return getAuthToken();
}

export function checkAuthLoader() {
  const token = getAuthToken();
  if (!token) {
    return redirect("/wallets/auth");
  }
  return token;
}

export function checkIfNoAuthLoader() {
  const token = getAuthToken();
  if (token) {
    return redirect("/wallets");
  }
  return token;
}
