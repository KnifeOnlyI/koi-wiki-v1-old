import {Injectable} from '@angular/core';
import {Objects} from '../../shared/utils/objects';
import {JwtToken} from '../auth/models/jwt-token';

/**
 * The service to manage JWT tokens.
 */
@Injectable()
export class JwtService {
  /**
   * Decode the specified token.
   *
   * @param token The token to decode
   *
   * @return The decoded token, NULL if it cannot be decoded
   */
  decode(token?: string | null): JwtToken | null {
    if (Objects.isNull(token) || Objects.isBlank(token)) {
      return null;
    }

    const parts = token?.split('.');

    if (Objects.isNull(parts) || parts!.length < 2) {
      return null;
    }

    const header = JSON.parse(atob(parts![0]));
    const payload = JSON.parse(atob(parts![1]));

    return {
      header,
      payload,
    };
  }
}
