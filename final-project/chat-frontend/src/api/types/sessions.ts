export interface SessionDto {
  id: string;
  expired: boolean;
  lastAccessedTime: string;
  details: SessionDetailsDto;
}

export interface SessionDetailsDto {
  remoteAddr: string;
  userAgent: string;
}
