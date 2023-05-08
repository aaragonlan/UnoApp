import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tab1 from './tab-1';
import Tab1Detail from './tab-1-detail';
import Tab1Update from './tab-1-update';
import Tab1DeleteDialog from './tab-1-delete-dialog';

const Tab1Routes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tab1 />} />
    <Route path="new" element={<Tab1Update />} />
    <Route path=":id">
      <Route index element={<Tab1Detail />} />
      <Route path="edit" element={<Tab1Update />} />
      <Route path="delete" element={<Tab1DeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default Tab1Routes;
