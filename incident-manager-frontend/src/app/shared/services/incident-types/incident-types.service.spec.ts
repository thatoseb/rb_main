import { TestBed } from '@angular/core/testing';

import { IncidentTypesService } from './incident-types.service';

describe('IncidentTypesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IncidentTypesService = TestBed.get(IncidentTypesService);
    expect(service).toBeTruthy();
  });
});
