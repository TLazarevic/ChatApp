import { TestBed } from '@angular/core/testing';

import { MessagesserviceService } from './messagesservice.service';

describe('MessagesserviceService', () => {
  let service: MessagesserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MessagesserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
