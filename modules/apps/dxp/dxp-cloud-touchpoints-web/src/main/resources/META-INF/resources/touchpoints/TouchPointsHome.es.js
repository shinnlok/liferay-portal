import Component from 'metal-component';
import templates from './TouchPointsHome.soy';
import Soy from 'metal-soy';

import 'dxp-cloud-sidebar/DXPSidebar.es';
import 'dxp-cloud-topbar/DXPTopbar.es';

class TouchPointsHome extends Component {
}

Soy.register(TouchPointsHome, templates);

export default TouchPointsHome;