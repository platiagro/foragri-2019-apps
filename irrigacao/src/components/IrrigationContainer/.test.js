import React from 'react';

import { shallow } from 'enzyme';

import MachineContainer from '.';

describe('MachineContainer component', () => {
  it('is expected render without crashing', () => {
    shallow(<MachineContainer />);
  });
});
