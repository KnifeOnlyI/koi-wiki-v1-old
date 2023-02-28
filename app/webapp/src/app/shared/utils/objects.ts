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

  /**
   * Create a copy of the specified value (JSON stringify and parse method).
   * Only properties are availables on the created copy, not methods.
   *
   * @param value The value to copy
   *
   * @return The copy
   */
  static cloneProperties<T>(value: T): T {
    if (this.isNull(value)) {
      return value;
    }

    return JSON.parse(JSON.stringify(value));
  }
}
