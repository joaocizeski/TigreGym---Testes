'use client';

import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import { logout } from "../redux/slices/authSlice";
import { limparAlunos } from "../redux/slices/alunosSlice";
import { RootState } from "../redux/store";

export default function Header() {

  const usuario = useSelector((state: RootState) => state.auth.usuario);
  const dispatch = useDispatch();
  const [carregado, setCarregado] = useState(false);

  useEffect(() => {
    setCarregado(true);
  }, []);

  return (
    <header className="w-full border-b border-zinc-800 bg-[#090d0f] px-6 py-3">
      <div className="flex min-h-14 items-center justify-end gap-5">

        <div className="flex items-center gap-3">

          <div className="flex h-9 w-9 items-center justify-center rounded-full border border-zinc-700 bg-zinc-900 text-zinc-300">
            <svg
              className="h-5 w-5"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
            >
              <circle cx="12" cy="8" r="3" />
              <path d="M5 21c.5-5 2.8-7.5 7-7.5S18.5 16 19 21" />
            </svg>
          </div>

          <div className="hidden sm:block">
            <p className="text-sm font-semibold text-white">
              {carregado && usuario
                ? usuario.nome.toLocaleUpperCase()
                : "Usuario não identificado"}
            </p>

            <p className="text-xs text-zinc-500">
              TigreGym
            </p>
          </div>

        </div>

        <button
          onClick={() => {
            dispatch(limparAlunos());
            dispatch(logout());
          }}
          className="rounded-lg border border-[#F5C518] px-4 py-2 text-sm font-semibold text-[#F5C518] transition hover:bg-[#F5C518] hover:text-black"
        >
          Sair
        </button>

      </div>
    </header>
  );
}
