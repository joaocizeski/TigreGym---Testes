import api from "./api";

export interface CepResponse {
  cep: string;
  logradouro: string;
  complemento: string;
  bairro: string;
  localidade: string;
  uf: string;
  erro?: boolean;
}

export async function buscarCep(cep: string): Promise<CepResponse> {
  const cepFormatado = cep.replace(/\D/g, "");
  const resposta = await api.get<CepResponse>('/integracoes/cep/' + cepFormatado);
  return resposta.data;
}
