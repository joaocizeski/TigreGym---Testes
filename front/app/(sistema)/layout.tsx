'use client';

import { useRouter } from "next/navigation";
import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import { RootState } from "../redux/store";
import { useEffect } from "react";
import { useSelector } from "react-redux";

export default function SistemaLayout({children}: {children: React.ReactNode;}) {

  const usuario = useSelector((state: RootState) => state.auth.usuario);
  const router = useRouter();

  useEffect(() => {

    if (usuario == null) {
      router.replace("/login");
    }

  }, [usuario, router]);

  if (usuario == null) {
    return null;
  }

  return (
    <div className="flex">
      <Sidebar />

      <div className="flex min-h-screen min-w-0 flex-1 flex-col">
        <Header />

        <main className="flex-1 overflow-x-hidden">
          {children}
        </main>

        <Footer />
      </div>
    </div>
  );
}
