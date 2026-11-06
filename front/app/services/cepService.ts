import api from "./api";

export interface EnderecoResponse {
  cep: string;
  logradouro: string;
  bairro: string;
  cidade: string;
  uf: string;
}

export async function buscarCep(cep: string): Promise<EnderecoResponse> {

  const resposta = await api.get<EnderecoResponse>(
    '/api/enderecos/' + cep
  );

  return resposta.data;
}
