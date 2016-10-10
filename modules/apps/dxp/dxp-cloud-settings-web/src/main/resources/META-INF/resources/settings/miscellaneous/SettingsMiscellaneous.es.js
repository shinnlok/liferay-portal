import Component from 'metal-component';
import templates from './SettingsMiscellaneous.soy';
import Soy from 'metal-soy';

import '../sections/SettingsSections.soy';

import 'dxp-cloud-sidebar/DXPSidebar.es';
import 'dxp-cloud-topbar/DXPTopbar.es';

class SettingsMiscellaneous extends Component {
}

Soy.register(SettingsMiscellaneous, templates);

export default SettingsMiscellaneous;