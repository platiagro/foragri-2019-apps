import React from 'react';

import { shallow } from 'enzyme';

import MachineContent from '.';

describe('MachineContent component', () => {
  it('is expected render without crashing', () => {
    shallow(<MachineContent />);
  });
});
