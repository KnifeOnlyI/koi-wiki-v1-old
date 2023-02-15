/**
 * Utilitary class for all objects.
 */
export class Objects {
  /**
   * Check if the specified value is null or undefined.
   *
   * @param value The value to check
   *
   * @return TRUE if the specified value is null or undefined, FALSE otherwise
   */
  static isNull(value: any): boolean {
    return value === undefined || value === null;
  }

  /**
   * Check if the specified value is null, undefined or blank.
   *
   * @param value The value to check
   *
   * @return TRUE if the specified value is null, undefined or blank
   */
  static isBlank(value: string | null | undefined): boolean {
    return Objects.isNull(value) || value?.trim().length === 0;
  }
}
