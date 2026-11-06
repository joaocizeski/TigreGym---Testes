import { Aluno } from "@/app/types/aluno";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface AlunosState {
  lista: Aluno[];
}

const initialState: AlunosState = {
  lista: [],
};

const alunosSlice = createSlice({
  name: "alunos",
  initialState,
  reducers: {
    setAlunos: (state, action: PayloadAction<Aluno[]>) => {
      state.lista = action.payload;
    },

    limparAlunos: (state) => {
      state.lista = [];
    },
  },
});

export const { setAlunos, limparAlunos } = alunosSlice.actions;
export default alunosSlice.reducer;
