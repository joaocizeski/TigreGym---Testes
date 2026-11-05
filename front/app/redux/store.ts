import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../redux/slices/authSlice";
import alunosReducer from "../redux/slices/alunosSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    alunos: alunosReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
export type AppDispath = AppDispatch;
