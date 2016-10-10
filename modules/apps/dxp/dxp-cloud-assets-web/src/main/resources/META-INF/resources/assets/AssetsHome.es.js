import Component from 'metal-component';
import templates from './AssetsHome.soy';
import Soy from 'metal-soy';

import 'dxp-cloud-sidebar/DXPSidebar.es';
import 'dxp-cloud-topbar/DXPTopbar.es';

class AssetsHome extends Component {
}

Soy.register(AssetsHome, templates);

export default AssetsHome;