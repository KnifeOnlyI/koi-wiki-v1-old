/**
 * Represent a JWT token.
 */
export interface JwtToken {
  header: {
    alg: string,
    kid: string,
    typ: string
  },
  payload: {
    acr: string,
    'allowed-origins': Array<string>,
    aud: string,
    auth_time: number,
    azp: string,
    email: string,
    email_verified: boolean,
    exp: number,
    family_name: string,
    given_name: string,
    iat: number,
    iss: string,
    jti: string,
    nonce: string,
    preferred_username: string,
    realm_access: {
      [key: string]: Array<string>
    },
    resource_access: {
      [key: string]: {
        [key: string]: Array<string>
      }
    },
    scope: string,
    session_state: string,
    sid: string,
    sub: string,
    type: string
  }
}
